import express from "express";
import routers from "../routers";
import {video , video_detail , delete_video , edit_video , upload} from "../controller/videocontroller"
const videoRouter = express.Router();

videoRouter.get(routers.video ,video);
videoRouter.get(routers.video_detail , video_detail);
videoRouter.get(routers.delete_video , delete_video);
videoRouter.get(routers.edit_video , edit_video);
videoRouter.get(routers.upload , upload);


export default videoRouter;
