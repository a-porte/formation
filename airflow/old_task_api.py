from airflow import DAG
from airflow.operators.python import PythonOperator
import pendulum

with DAG(
        "my_old_dag",
        schedule=None,
        catchup=False,
        start_date=pendulum.datetime(2023,1,1)
) as dag:
    def my_print():
        print("Hi I'm printing!")

    def get_data(**kwargs):
        ti = kwargs["ti"]
        data = {"ichi":1, "ni":2}
        print("data from get data :", data)
        ti.xcom_push("to_process", data)

    def process_data(**kwargs):
        ti = kwargs["ti"]
        input= ti.xcom_pull(task_ids="get_data", key = "to_process")

        for k in input:
            print(">> (value+2) is ", input[k] + 2)

    t_my_print = PythonOperator(
        task_id="my_print",
        python_callable=my_print
    )
    t_process_data = PythonOperator(
        task_id="process_data",
        python_callable=process_data
    )
    t_get_data = PythonOperator(
        task_id="get_data",
        python_callable=get_data
    )


    t_my_print >> t_get_data >> t_process_data # >> t_my_print # is illegal, otherwise there would be a cycle and the DAG wouldn't be a directed *acyclic* graph anymore