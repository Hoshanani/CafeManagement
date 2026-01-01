@echo off
chcp 65001 > nul

rem === Compile all Java files under src into out ===
if not exist out mkdir out

javac -encoding UTF-8 -d out -sourcepath src ^
  src\app\Main.java

rem === Run ===
java -cp out app.Main
pause
