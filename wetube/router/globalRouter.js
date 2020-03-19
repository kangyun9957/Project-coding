import express from "express";
import routers from "../routers";
import {home , search} from "../controller/videocontroller"
import {postjoin , getjoin,  login , logout } from "../controller/usercontroller"
const globalRouter = express.Router();

globalRouter.get(routers.join , getjoin);
globalRouter.post(routers.join , postjoin);



globalRouter.get(routers.home , home);
globalRouter.get(routers.login , login)
globalRouter.get(routers.logout, logout);

globalRouter.get(routers.search , search);

export default globalRouter;
