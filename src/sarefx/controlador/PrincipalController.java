/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sarefx.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Alex
 */
public class PrincipalController implements Initializable {

    @FXML
    private MenuItem mi_categoria;
    @FXML
    private BorderPane pnl_principal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setData(HashMap<String,Object> context){          //crear este metodo en todos los controladores
        
        System.out.print(context);
    }

    @FXML
    private void abrirUsuario(ActionEvent event) {
    }

    @FXML
    private void abrirCategoria(ActionEvent event) {
        
        try{
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sarefx/gui/vista/CategoriasFXML.fxml"));
            
            Parent principal = loader.load();
            
            pnl_principal.setCenter(principal);
 
        }catch(IOException ex){
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
}
