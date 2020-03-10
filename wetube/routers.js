//global
const Home = "/";
const Login = "/login";
const Logout ="/logout";
const Join = "/join";
//user
const User = "/user";
const Edit_profile = "/edit-profile";
const Edit_password = "/edit-password";
const User_detail = "/:id";
//video
const Video = "/video";
const Upload = "/upload";
const Edit_video = "/:id/edit";
const Delete_video ="/:id/delete";
const Video_detail = "/:id/detail";
const Search = "/search";

const routers = {

    home : Home,
    search : Search,
    login : Login,
    logout : Logout,
    join : Join,
    user : User,
    edit_profile : Edit_profile,
    edit_password : Edit_password,
    user_detail : User_detail,
    video : Video,
    upload : Upload,
    edit_video : Edit_video,
    delete_video : Delete_video,
    video_detail : Video_detail
};

export default routers;