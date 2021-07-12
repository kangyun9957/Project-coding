import routers from "../routers";


export const user = (req,res) => res.render("User", {PageTitle : "User"});
export const user_detail=(req,res) => res.render("Detail", {PageTitle : "Detail"});
export const edit_password =(req,res) => res.render("Password", {PageTitle : "Password"});
export const edit_profile =(req,res) => res.render("Profile", {PageTitle : "Profile"});
export const logout = (req,res) => res.render("Logout", {PageTitle : "Logout"});
export const login = (req,res) => res.render("Login", {PageTitle : "Login"});
export const postjoin = (req,res) => {
    const {body : {name, email ,password ,password2}} = req;
    if(password !== password2){

        
        res.render("Join", {PageTitle : "Join"});
    }
    else{

        res.redirect(routers.home);
    }
};
export const getjoin = (req,res) => {
    res.render("Join", {PageTitle : "Join"})};
