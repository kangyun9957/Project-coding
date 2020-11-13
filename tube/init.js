import app from "./app";

const Port = 3000;

const handleListening = () => console.log(`Listening on: http://localhost:${Port}`);
app.listen(Port,handleListening);