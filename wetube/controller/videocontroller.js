export const video = (req,res) => res.render("video");
export const video_detail =(req,res) => res.render("video_detail", {PageTitle : "video_detail"});
export const delete_video = (req,res) => res.render("delete",  {PageTitle : "delete_video"});
export const edit_video =(req,res) => res.render("edit_video", {PageTitle : "edit_video"});
export const upload = (req,res) => res.render("upload", {PageTitle : "upload"});
export const home = (req,res) => res.render("home", {PageTitle : "home"});
export const search = (req,res) =>{ 
    const {query :{term : SearchingBy}} = req;
    console.log(SearchingBy);
    res.render("search", {PageTitle : "search", SearchingBy})

};