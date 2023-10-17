import pendulum
from airflow.decorators import dag, task

@dag(
    schedule=None, #can be set to a cron preset or a 'custom' one, e.g.: "@hourly" or "<mn> <h> <d> <M> <day>"
    start_date=pendulum.datetime(2023, 1, 1, tz = "UTC")
    catchup=False # if True, then will execute DAG for every date since start_date
)
def my_dag():

    @task
    def my_print():
        print("Hi I'm printing!")

    @task()
    def get_data():
        data = {"ichi":1, "ni":2}
        print("data from get data :", data)
        return data

    @task()
    def process_data(input: dict):
        for k in input:
            print(">> (value+2) is ", input[k] + 2)

    # The order of the tasks will only be guaranteed for to_process and process_data
    # my_print occurrences *may even be called between* to_process and process_data
    # executing DAG with --show-dagrun flag will print :
    # digraph my_dag {
    #     graph [label=my_dag labelloc=t rankdir=LR]
    # get_data [color=white fillcolor=green label=get_data shape=rectangle style="filled,rounded"]
    # my_print [color=white fillcolor=green label=print_hourly shape=rectangle style="filled,rounded"]
    # my_print__1 [color=white fillcolor=green label=print_hourly__1 shape=rectangle style="filled,rounded"]
    # to_process [color=white fillcolor=green label=process_data shape=rectangle style="filled,rounded"]
    # get_data -> to_process
    # }
    my_print()
    to_process = get_data()
    print("I'm between 2 tasks !") #actually, won't be printed at all
    process_data(to_process)
    my_print()

my_dag()
