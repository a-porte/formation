# Airflow

## Definitions
- operator : unit of work, classic approach to defining DAGs, can be through [TaskFlow API](https://airflow.apache.org/docs/apache-airflow/stable/tutorial/taskflow.html) to have more pythonic code
    - ex : `BashOperator`, `PythonOperator`, etc  
- task : an operator's instantiation, unique identifier is `task_id`. Task can receive args explicitly or _via_ `default_args`, which are passed to `DAG` constructor, default args can be overriden.
Task support [Jinja templating](https://jinja.palletsprojects.com/en/3.0.x/)
- DAG : structure defined in a Python file whish is in fact a configuration one. There should be **no data processing** in here ! The file **is intended to be interpreted quickly** by the scheduler.

## DAG configuration file's structure
### "Default" API (prior to Airflow 2.0)
````python
with DAG(
  <args>,
  default_args = {...}
)  dag:
  def my_func(**kwargs):
    ti = kwargs["ti"] #task instance
    <var> = ti.xcom_pull(task_ids="...", key ="...") #cross communication enable tasks communication
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
### [TaskFlow API](https://airflow.apache.org/docs/apache-airflow/stable/tutorial/taskflow.html) API 
````python

````

