# 服务端server

import socket

#创建socket对象
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  # 必须和客户端保持一致
#需要自己绑定一个ip地址和端口号
s.bind(('localhost', 8000))
#服务端监听操作时刻注意是否有客户端请求发来
s.listen(3)  # 可以同时监听3个，但是这里只有一个客户请求，因为没有写多线程
#同意连接请求
s1,addr = s.accept()  # s是服务端的socket对象s1是接入的客户端socket对象
print(addr)
while True:
#revice接收数据
    data = s1.recv(1024)  # 设定一次可以接收1024字节大小
    print(data.decode())  # 传过来的字节流需要用decode()解码
    data1=input("Please input data sending to client:")
    s1.send(data1.encode())
    if data1=='bye':
        break