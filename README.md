# EventStream: Scalable Data Pipeline for Event Processing

## Overview

EventStream is a scalable data pipeline designed to ingest, process, and analyze event data from various sources. Utilizing Apache NiFi for data ingestion, Apache Spark for processing, Apache Airflow for orchestration, and MinIO for storage, this project provides a robust framework for handling large volumes of event data efficiently.

## Architecture

[EventStream Architecture](architecture.png)

### Architecture Components

1. **Apache NiFi**: Handles data ingestion from CSV files, converting them to JSON format and storing them in MinIO.
2. **MinIO**: Acts as a local object storage solution, mimicking Amazon S3 for storing raw and processed data.
3. **Apache Spark**: Processes data using Scala, performing advanced transformations and aggregations.
4. **Apache Airflow**: Orchestrates the workflow, scheduling tasks for data ingestion, processing, and storage.
5. **Prometheus & Grafana**: Provide monitoring and visualization of system metrics for NiFi, Airflow, Spark, and MinIO.

## Features

- **Scalable Data Ingestion**: Efficiently ingest large datasets using Apache NiFi.
- **Advanced Data Processing**: Perform industry-grade transformations and aggregations with Apache Spark.
- **Robust Orchestration**: Manage and schedule workflows using Apache Airflow.
- **Comprehensive Monitoring**: Track system performance and health using Prometheus and Grafana.

## Setup Instructions

### Prerequisites

- Docker and Docker Compose
- Git LFS (for managing large data files)

### Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/yourusername/eventstream.git
   cd eventstream
   ```

2. **Initialize Git LFS**

   ```bash
   git lfs install
   git lfs track "*.csv"
   ```

3. **Build and Run Services**

   ```bash
   docker-compose up -d
   ```

4. **Access Services**

   - **MinIO**: [http://localhost:9000](http://localhost:9000)
   - **NiFi**: [http://localhost:8080](http://localhost:8080)
   - **Airflow**: [http://localhost:8081](http://localhost:8081)
   - **Spark UI**: [http://localhost:8082](http://localhost:8082)
   - **Prometheus**: [http://localhost:9090](http://localhost:9090)
   - **Grafana**: [http://localhost:3000](http://localhost:3000)

## Usage

- **Data Ingestion**: Place CSV files in the `data/` directory. NiFi will ingest and convert them to JSON.
- **Data Processing**: Run the Scala script using Spark to process and transform data.
- **Monitoring**: Use Grafana dashboards to visualize metrics collected by Prometheus.

## Maintenance

- Regularly update Scala scripts and Airflow DAGs to optimize performance.
- Implement data quality checks to ensure data integrity.

## License

This project is licensed under the MIT License.

## Contact

For questions or support, please contact [zwiswamuridili990@gmail.com](mailto:zwiswamuridili990@gmail.com).

### Architecture Diagram

To create the architecture diagram, you can use tools like Lucidchart, Draw.io, or any diagramming software. Here's a description of the diagram:

- **NiFi**: Ingests data from the `data/` directory, converts it to JSON, and stores it in MinIO.
- **MinIO**: Acts as storage for raw and processed data.
- **Spark**: Processes data using Scala, performing transformations and aggregations.
- **Airflow**: Orchestrates the workflow, managing task dependencies and scheduling.
- **Prometheus & Grafana**: Monitors system metrics and visualizes them in dashboards.
