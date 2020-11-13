import express from "express";
import routers from "../routers";
import {user , user_detail , edit_password ,edit_profile} from "../controller/usercontroller"
const userRouter = express.Router();

userRouter.get(routers.edit_password , edit_password);
userRouter.get(routers.edit_profile, edit_profile);
userRouter.get(routers.user_detail , user_detail);


export default userRouter;

