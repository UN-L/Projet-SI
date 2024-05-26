# Projet-SI

Starting server to host the plugin (depending on config) : 

Be in the "target" directory
 
```
python3 -m http.server 8000 --bind 172.20.10.3
ngrok http --domain=settled-leopard-flowing.ngrok-free.app 172.20.10.3:8000
```

URL :
https://settled-leopard-flowing.ngrok-free.app/
