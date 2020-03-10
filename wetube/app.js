import "core-js";
import express from "express";
import morgan from "morgan";
import helmet from "helmet";
import cookie from "cookie-parser";
import body from "body-parser";
import globalRouter from "./router/globalRouter"
import userRouter from "./router/userRouter"
import videoRouter from "./router/videoRouter"
import routers from "./routers"
import { LocalMiddleWares } from "./middlewares";
const app = express();
app.use(helmet());
app.set("view engine" , "pug");
app.use(cookie());
app.use(body.json());
app.use(body.urlencoded({extended : true}));
app.use(morgan("combined"));
app.use(LocalMiddleWares);
app.use(routers.user, userRouter);
app.use(routers.home,globalRouter);

app.use(routers.video , videoRouter)

export default app;