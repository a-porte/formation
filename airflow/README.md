# Airflow

## Definitions
- task : "basic unit of execution" of Airflow, dependencies between several tasks form a DAG. They have an unique identifier (`task_id`). Tasks can receive args explicitly or _via_ `default_args`, which are passed to `DAG` constructor ; default args can be overriden. There is 3 kinds of task :
  - [Operator](https://airflow.apache.org/docs/apache-airflow/stable/core-concepts/operators.html) : template for predefined tasks (e.g. BashOperator, etc), classical approach to defining DAGs
  - [Sensor](https://airflow.apache.org/docs/apache-airflow/stable/core-concepts/sensors.html) : special type of ``operator`` designed for waiting for an event (time-based or not) or a file
  - "[TaskFlow](https://airflow.apache.org/docs/apache-airflow/stable/tutorial/taskflow.html)-decorated" one : a Python function decorated by `@task`
- DAG : structure defined in a Python file (which is in fact a configuration one). There should be **no data processing** inside it ! The file **is intended to be interpreted quickly** by the scheduler.
- [Logical date](https://airflow.apache.org/docs/apache-airflow/stable/core-concepts/dags.html#concepts-dag-run) (or `@deprecated` *execution date*) : Functional date used by the DAG. Quite different from the date of the execution of the DAG. For ex., we're on 05/26 and want to process one-week-old data : the logical date will be 05/19.
- [Data interval](https://airflow.apache.org/docs/apache-airflow/stable/core-concepts/dag-run.html#data-interval) : Represents the time range over which a DAG is executed, can be `@hourly`, `@daily`, etc. ``Logical date`` represents the beginning of a `data interval`. If scheduled, the DAG will be first executed once `logical date + 1st data interval` is ended. *Indeed, if we want to process data daily, the DAGs will be executed once each day is over.*
- `Catchup` and `backfill` : actually the same thing but occurring at different moments :
  - [catchup](https://airflow.apache.org/docs/apache-airflow/stable/core-concepts/dag-run.html#catchup) : when defining a DAG, if `catchup` argument is set to `false`, the scheduler will execute a DAG for every date where there is no `logical date`.
  - [backfill](https://airflow.apache.org/docs/apache-airflow/stable/core-concepts/dag-run.html#backfill) : capacity to execute a DAG over a period of time (prior to `start_date`!), executed on demand *via* `airflow dags backfill -s <start_date> -e <end_date> <DAG id>`
- `DAG` run : instantiation of a DAG
- [Dynamic Task Mapping](https://airflow.apache.org/docs/apache-airflow/stable/authoring-and-scheduling/dynamic-task-mapping.html) : way to create task according to input data. A reduce task is not necessary. Such tasks are represented by "<task name **[]**>" in UI.

Note: Tasks support [Jinja templating](https://jinja.palletsprojects.com/en/3.0.x/)

## Useful command lines
### When writing a new DAG
- ``python <path/to/dag>.py #should return no error`` 

### Miscellaneous

## DAG configuration file's structure
### "Default" API (prior to Airflow 2.0)
````python
from airflow import DAG

from airflow.operator.<ope> import <myOperator>
from airflow.operator.python import <PythonOperator>

with DAG(
    <args>,
    default_args = {...}
)  dag:
  def my_func(**kwargs):
    ti = kwargs["ti"] #task instance
    <var> = ti.xcom_pull(task_ids="...", key ="...") #cross communication enables tasks communication
    <processed_var> = <processing on <var>>
    ti.push("<key>", <processed_var>)

    <my_task_object_1> = PythonOperator(
      task_id=...,
      python_callable=my_func
                      <other args>
    )
    <my_task_object_2> = <myOperator>(
        task_id=...,
    <overridden default arg, if any>
    <other args>
    )
    ...
    <my_task_object_n> = <myOperator>(
        task_id=...,
    <other args>
    )

    # 2 ways to say that my_task_object_2 depends and  my_task_object_1
    my_task_object_1.set_downstream(my_task_object_2)
    my_task_object_1 >> my_task_object_2

    my_task_object_1 >> [my_task_object_2, my_task_object_n]

    my_task_object_2 << my_task_object_1
    # is the same as
    my_task_object_2.set_upstream(my_task_object_1)
````
### [TaskFlow API](https://airflow.apache.org/docs/apache-airflow/stable/tutorial/taskflow.html) 
````python
from airflow.decorators import dag, task

@dag(
    <args>
)
def <DAG_s_name>():
    @task(<args if need be>)
    def <my_fun>():
        #some stuff
    
    @task(<args if need be>)
    def <my_second_fun>(<args>):
        #some stuff again
        
    <var> = <my_func>
    <my_second_fun>(<var>)

<DAG_s_name>()
````


## Additional resources
- https://docs.astronomer.io/learn/intro-to-airflow