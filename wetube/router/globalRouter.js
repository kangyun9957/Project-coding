import express from "express";
import routers from "../routers";
const globalRouter = express.Router();

globalRouter.get(routers.home , (req,res) => res.send("Home"))
globalRouter.get(routers.login , (req,res) => res.send("Login"))
globalRouter.get(routers.logout, (req,res) => res.send("Logout"))
globalRouter.get(routers.join , (req,res) => res.send("Join"))

export default globalRouter;
