/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sarefx.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sarefx.modelo.pojos.Categoria;

/**
 * FXML Controller class
 *
 * @author Alex
 */
public class FomCategoriaFXMLController implements Initializable {

    @FXML
    private TextField txt_idCategoria;
    @FXML
    private TextField txt_nombreCategoria;
    @FXML
    private TextField txt_activoCategoria;
    @FXML
    private TextField txt_ordenCategoria;
    @FXML
    private Button btn_guardar;
    @FXML
    private Button btn_cancelar;
    
    
    Categoria categoria = null;                             //se crearon
    Boolean isnew=false;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void guardar(ActionEvent event) {
    }

    @FXML
    private void cancelar(ActionEvent event) {
    }
    
    public void setData(Categoria categoria, Boolean isnew){  //null al categoria al de nuevo y boolean true
        this.categoria=categoria;
        this.isnew=isnew;
        this.cargarCategoria();
    }
    
    public void cargarCategoria(){
        this.txt_activoCategoria.setText(categoria.getActivo());    //cargar los datos
        this.txt_idCategoria.setText(categoria.getIdCategoria().toString());
        this.txt_nombreCategoria.setText(categoria.getNombre());
        this.txt_ordenCategoria.setText(categoria.getOrden().toString());
    }
}
