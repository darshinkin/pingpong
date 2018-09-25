Для запуска программы необходимо использовать следующую комманду:
mvn clean package && java -jar target/pingpong-1.0-SNAPSHOT.jar

Вывод программы будет примерно следующим:

Player1: send text
Player2: send text1
------ Player1 recieved 1 times.
Player1: send text2
Player2: send text3
------ Player1 recieved 3 times.
Player1: send text4
------ Player1 recieved 5 times.
Player2: send text5
Player1: send text6
------ Player1 recieved 7 times.
Player2: send text7
Player1: send text8
------ Player1 recieved 9 times.
Player2: send text9
Player1: send text10
------ Player1 recieved 11 times.
Player2: send text11
Player1: send text12
------ Player1 recieved 13 times.
Player2: send text13
Player1: send text14
------ Player1 recieved 15 times.
Player2: send text15
Player1: send text16
------ Player1 recieved 17 times.
Player2: send text17
Player1: send text18
------ Player1 recieved 19 times.
Player2: send text19
Player1: send text20
------ Player1 recieved 21 times.
Player2: send text21
Player1: send text22
Player2: send text23
------ Player1 recieved 23 times.
Player1: send text24
------ Player1 recieved 25 times.
Player2: send text25
Player1: send text26
------ Player1 recieved 27 times.
Player2: send text27
Player1: send text28
------ Player1 recieved 29 times.
Thread-1 with player [Player1] thread was shutdown
Player2: send text29
Thread-2 with player [Player2] thread was shutdown





Комментарии:
Такая колизия в выводе в консоль:
Player1: send text20
------ Player1 recieved 21 times.
Player2: send text21
происходит за счет того, что есть возможность более медленного вывода на консоль в одном потоке
по сравнению с отпревкой сообщения в очередь и следующей распечаткой сообщения в другом потоке. Это нормальная ситуация.