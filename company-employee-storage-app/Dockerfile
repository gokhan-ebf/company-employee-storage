FROM openjdk:8
MAINTAINER Gokhan Kuyucak <gokhank@gmail.com>

ARG VAR_COMPANY_APP=company-employee-storage-app


ENV COMPANY_APP="$VAR_COMPANY_APP"

WORKDIR /
ADD "target/$VAR_COMPANY_APP*.jar" "company-employee-storage.jar"

# RUN Job
ENTRYPOINT [ \
    "java", \
    "-XX:-OmitStackTraceInFastThrow", \
    "-XX:+ExitOnOutOfMemoryError", \
    "-jar", "company-employee-storage.jar" \
    ]
