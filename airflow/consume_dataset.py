import pendulum
from airflow.decorators import dag, task
from airflow.datasets import Dataset

#TaskFlow API

# for reference
# https://docs.astronomer.io/learn/airflow-datasets
DS = Dataset("s3://this-dataset-doesnt-get-triggered") # name coming from example_datasets.py
@dag(
    dag_id="ds_prod",
    schedule=None,
    start_date=pendulum.datetime(2023, 1, 1, tz = "UTC"),
    catchup=False,
    tags=["deal_with_this_dataset_doesnt_get_triggered"]
)
def my_ds_producer():

    @task(outlets=[DS])
    def produce_ds():
        print("Hi from my_ds_producer#produce_ds")
        return DS

    produce_ds()

my_ds_producer()

@dag(
    dag_id="ds_conso",
    schedule=[DS],
    start_date=pendulum.datetime(2023, 1, 1, tz = "UTC"),
    catchup=False,
    tags=["deal_with_this_dataset_doesnt_get_triggered"]
)
def my_ds_consumer():
    @task
    def consume_ds(data):
        print("Hi from my_ds_consumer#consume_ds")

    consume_ds(DS)



my_ds_consumer()