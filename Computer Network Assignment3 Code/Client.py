# client 客户端

# TCP建立连接
import socket   #导入模块
# SOCK_STREAM---TCP协议方式
# AF_INET----ipv4地址,加6即是ipv6
#创建socket对象：指定传输协议
s=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
#建立连接发送连接请求  ip地址和端口号
s.connect(('localhost',8000))
while True:
    data=input("Please input data sending to server:")
    s.send(data.encode()) #发送字节流需要用encode转码字符串成字节，不然无法发送文件
    data1=s.recv(1024) #一次传输1024字节
    print(data1.decode())
    if data=='bye':
        break