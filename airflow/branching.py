from airflow.decorators import dag, task
from airflow.models.baseoperator import chain
from airflow.operators.empty import EmptyOperator
import random
import pendulum
from airflow.utils.edgemodifier import Label

"""DAGs coming from Example https://docs.astronomer.io/learn/airflow-branch-operator
"""


@dag(
    start_date=pendulum.datetime(2023, 1, 1),
    catchup=False,
    schedule="@daily",
    tags=["branch"],
)
def my_branching_dag():
    # run_this_first = EmptyOperator(task_id="run_this_first")
    @task(task_id="empty")
    def run_this_first():
        print("I'm empty")

    options = ["branch_a", "branch_b", "branch_c", "branch_d"]

    @task.branch(task_id="branching")
    def random_choice(choices):
        c = random.choice(choices)
        print(f"Choosing : {c}")
        return c

    random_choice_instance = random_choice(choices=options)

    run_this_first() >> random_choice_instance

    join = EmptyOperator(
   # @task(
        task_id="join",
        trigger_rule="none_failed_min_one_success",  # enables to skip the unexecuted branches, default is "all_success"
    )#)
    #def join(): # if used, then there will be as main 'join' tasks as items in 'options'
    #    pass

    for option in options:
        # t = EmptyOperator(task_id=option)
        @task(task_id=option)
        def t():
            pass

        #empty_follow = EmptyOperator(task_id="follow_" + option)
        @task(task_id="follow_" + option)
        def empty_follow():
            pass

        # Label is optional here, but it can help identify more complex branches
        # can't use a @task decorated method for "join" method, otherwise it would duplicate it at runtime
        random_choice_instance >> Label(option) >> t() >> empty_follow() >> join
        # chain(random_choice_instance, Label(option) , t() , empty_follow() , join) #alternate way


my_branching_dag()



@dag(
    start_date=pendulum.datetime(2023, 1, 1),
    schedule="@daily",
    catchup=False,
    tags=["branch"]
)
def short_circuit_operator_decorator_example():
    @task.short_circuit
    def condition_is_true():
        return True

    @task.short_circuit
    def condition_is_false():
        return False

    ds_true = [EmptyOperator(task_id='true_' + str(i)) for i in [1, 2]]
    ds_false = [EmptyOperator(task_id='false_' + str(i)) for i in [1, 2]]

    chain(condition_is_true(), *ds_true) # *-operator here is mandatory to chain "condition_is_true" with true_1 and true_2
    chain(condition_is_false(), *ds_false) # otherwise "condition_is_true" would be chained with "true_1" and "true_2" which would parallelized
    # condition_is_false() >> [f for f in ds_false] # does not work




short_circuit_operator_decorator_example()
