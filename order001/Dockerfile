#java8环境
FROM java:8

#将宿主机下的jar文件放到容器app.jar
ADD *.jar app.jar

#申明端口
EXPOSE 5000

#最后运行 /app.jar 就是说在容器根目录下
ENTRYPOINT ["java", "-jar","/app.jar"]
