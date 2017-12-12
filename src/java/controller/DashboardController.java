/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Roseanne Page
 */
@ManagedBean(name = "DashboardController")
@ViewScoped
public class DashboardController {

  //to get the index page
    public String getDashboardPage(){
        return "index";
    }

    
}