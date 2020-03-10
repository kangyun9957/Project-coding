import routers from "./routers";

export const LocalMiddleWares = (req, res ,next) =>{

    res.locals.sitename = "WeTube";
    res.locals.routers = routers
    next();



}