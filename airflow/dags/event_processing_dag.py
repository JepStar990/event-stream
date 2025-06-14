from airflow import DAG
from airflow.operators.dummy_operator import DummyOperator
from airflow.operators.bash_operator import BashOperator
from datetime import datetime, timedelta

# Default arguments for the DAG
default_args = {
    'owner': 'airflow',
    'depends_on_past': False,
    'start_date': datetime(2023, 10, 1),
    'retries': 1,
    'retry_delay': timedelta(minutes=5),
}

# Define the DAG
dag = DAG(
    'event_processing_dag',
    default_args=default_args,
    description='A DAG to orchestrate data ingestion and processing',
    schedule_interval='@daily',  # Run daily
)

# Task 1: Ingest Data using NiFi
ingest_data = BashOperator(
    task_id='ingest_data',
    bash_command='curl -X POST http://localhost:8080/nifi-api/process-groups/root/process-groups/{process_group_id}/run-status',
    dag=dag,
)

# Task 2: Process Data using Spark
process_data = BashOperator(
    task_id='process_data',
    bash_command='spark-submit --class DataProcessing --master local[*] /opt/spark/DataProcessing.scala',
    dag=dag,
)

# Task 3: Store Processed Data
store_processed_data = DummyOperator(
    task_id='store_processed_data',
    dag=dag,
)

# Define task dependencies
ingest_data >> process_data >> store_processed_data
