import express from "express";
import routers from "../routers";
const userRouter = express.Router();

userRouter.get(routers.user , (req,res) => res.send("User"));
userRouter.get(routers.user_detail , (req,res) => res.send("Detail"));
userRouter.get(routers.edit_password , (req,res) => res.send("Password"));
userRouter.get(routers.edit_profile, (req,res) => res.send("Profile"));


export default userRouter;

