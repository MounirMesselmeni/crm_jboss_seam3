/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insat.gl5.crm_pfa.web.controller.profil;

import com.insat.gl5.crm_pfa.model.BackendUser;
import com.insat.gl5.crm_pfa.model.Contact;
import com.insat.gl5.crm_pfa.service.qualifier.CurrentContact;
import com.insat.gl5.crm_pfa.service.qualifier.CurrentUser;
import com.insat.gl5.crm_pfa.service.security.UserProfileService;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.jboss.seam.international.status.Messages;

/**
 *
 * @author Mounir Messelmeni, contact: messelmeni.mounir@gmail.com
 */
@Named
@RequestScoped
public class ChangePasswordAction implements Serializable {

    @Inject
    private Messages messages;
    @Inject
    private UserProfileService profileService;
    @Inject
    @CurrentContact
    private Contact currentUser;
    @Inject
    @CurrentUser
    private BackendUser backendUser;
    private String oldPassword = null;
    private String newPassword = null;
    private String confirmPassword = null;

    /**
     * Change current user's password
     */
    public String changePassword() {
        if (checkConfirmPassword()) {
            return null;
        }
        if (updatePassword()) {
            if(currentUser == null)
            return "/backoffice/home.jsf?faces-redirect=true";
            else
                return "/frontoffice/home.jsf?faces-redirect=true";
        }
        this.messages.error("Mot de passe invalide.");
        return null;
    }

    private boolean updatePassword() {
        if (this.currentUser != null) {
            if (this.profileService.updatePassword(newPassword, currentUser.getLogin(), oldPassword)) {
                this.messages.info("Mot de passe à jour");
                return true;
            }
        } else if (this.backendUser != null) {
            if (this.profileService.updatePassword(newPassword, backendUser.getLogin(), oldPassword)) {
                this.messages.info("Mot de passe à jour");
                return true;
            }
        }
        return false;
    }

    private boolean checkConfirmPassword() {
        if (!this.confirmPassword.equals(this.newPassword)) {
            this.messages.error("Confirmation du mot de passe invalide");
            return true;
        }
        return false;
    }

    /**
     * @return the oldPassword
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * @param oldPassword the oldPassword to set
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    /**
     * @return the newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * @param newPassword the newPassword to set
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * @return the confirmPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * @param confirmPassword the confirmPassword to set
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
