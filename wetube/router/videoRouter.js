import express from "express";
import routers from "../routers";
const videoRouter = express.Router();

videoRouter.get(routers.video , (req,res) => res.send("Video"));
videoRouter.get(routers.video_detail , (req,res) => res.send("Video_detail"));
videoRouter.get(routers.delete_video , (req,res) => res.send("Delete"));
videoRouter.get(routers.edit_video , (req,res) => res.send("Edit_video"));
videoRouter.get(routers.upload , (req,res) => res.send("Upload"));


export default videoRouter;
