set JAVA_OPTS= %JAVA_OPTS% -Dlog4j.configurationFile=config/log4j2.xml -Duser.language=es -Duser.country=AR -Duser.timezone=GMT-03:00

java %JAVA_OPTS% -jar ${project.artifactId}-${project.version}.jar --spring.profiles.active=prod 