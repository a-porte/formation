import pendulum
from airflow.decorators import dag, task

@dag(
    schedule=None,
    start_data=pendulum.datetime(2023, 1, 1, tz = "UTC")
    catchup=False
)
def my_dag():

    @task
    def my_print():
        print("Hi I'm printing!")

    my_print()

my_dag()
