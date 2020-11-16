package com.user;

public interface UserHandler {
    public String getCurrentUsername();
    public void getAccessPeriod();
}

class AdminHandler implements UserHandler{
    private Admin user;

    public String getCurrentUsername(){
        return user.getUsername();
    }

    @Override
    public void getAccessPeriod() {
        //todo fill in
    }

    public void createCourse(){

    }
    public void editCourse(){

    }
    public void checkCourse(){

    }
    public String[] getStudentList(){
        return null;
    }
    public int checkVacancy(){
        return 0;
    }

}