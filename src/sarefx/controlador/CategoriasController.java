/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sarefx.controlador;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sarefx.api.requests.Requests;
import sarefx.modelo.pojos.Catalogo;
import sarefx.modelo.pojos.Categoria;

/**
 * FXML Controller class
 *
 * @author Alex
 */
public class CategoriasController implements Initializable {

    @FXML
    private Pane pnl_CategoriaCatalogo;
    @FXML
    private Pane pnl_busqueda;
    @FXML
    private TextField txt_buscar;
    @FXML
    private Button btn_buscar;
    @FXML
    private Button btn_limpiar;
    @FXML
    private Pane pnl_barraDeBotonesCategorias;
    @FXML
    private Button btn_nuevaCategoria;
    @FXML
    private Button btn_editarCategoria;
    @FXML
    private Button btn_activarCategoria;
    @FXML
    private Button btn_desactivarCategoria;
    @FXML
    private TableView<Categoria> tbl_categoria;
    @FXML
    private TableColumn<Categoria, Integer> tsl_categoriaIdCategoria;
    @FXML
    private TableColumn<Categoria, String> tsl_categoriaNombre;
    @FXML
    private TableColumn<Categoria, String> tsl_categoriaActivo;
    @FXML
    private TableColumn<Categoria, Integer> tsl_categoriaOrden;
    @FXML
    private Pane pnl_barraDeBotonesCatalogos;
    @FXML
    private TableView<Catalogo> tbl_catalogo;
    @FXML
    private TableColumn<Catalogo, Integer> tsl_catalogoIdCatalogo;
    @FXML
    private TableColumn<Catalogo, String> tsl_catalogoNombre;
    @FXML
    private TableColumn<Catalogo, String> tsl_catalogoIdActivo;
    @FXML
    private TableColumn<Catalogo, Integer> tsl_catalogoOrden;
    @FXML
    private Button btn_nuevoC;
    @FXML
    private Button btn_editarC;
    @FXML
    private Button btn_activarC;
    @FXML
    private Button btn_desactivarC;
    
    Categoria categoria = null;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buscarIdCategoria(ActionEvent event) {
        
        this.cargarCategoria();
    }

    @FXML
    private void limpiar(ActionEvent event) {
    }

    @FXML
    private void nuevaCategoria(ActionEvent event) {
    }

    @FXML
    private void editarCategoria(ActionEvent event) {
        
        //validar que la tabla no este vacia y luego que no seleccion un intem
        if(this.categoria != null){
            
            try {
                Stage stage = new Stage();
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sarefx/gui/vista/FomCategoriaFXML.fxml"));
                
                Parent formCategoria = loader.load();  //nodo raiz
                
                FomCategoriaFXMLController ctrl = loader.getController();
                
                ctrl.setData(this.categoria,false);
                
                Scene scene = new Scene(formCategoria);
                
                stage.setScene(scene);
               
                stage.setTitle("SARE (Editar Categoria)");
                stage.setResizable(false);
                stage.show();
                
            } catch (IOException ex) {
                Logger.getLogger(CategoriasController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Debe seleccionar una categoria...");
            alert.showAndWait();
        }
    }

    @FXML
    private void activarCategoria(ActionEvent event) {
    }

    @FXML
    private void desactivarCategoria(ActionEvent event) {
    }
    
    
    public void cargarCategoria(){
        String respuesta = "";
        tbl_categoria.getItems().clear();   //limpia la tabla
        
        respuesta = Requests.get("/categoria/getAllCategoria/");
        Gson gson = new Gson();
        
        //Definimos un typetoken que representa una lista de objeto categoria
        TypeToken<List<Categoria>> token = new TypeToken<List<Categoria>>(){   
        };
        
        //utilizamos el metodo fromJson() de ña clase Gson para convertir eñ JSon en ima lista de objeto categoria
        List<Categoria> listaCategoria = gson.fromJson(respuesta, token.getType());
        
        tsl_categoriaIdCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
        tsl_categoriaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tsl_categoriaActivo.setCellValueFactory(new PropertyValueFactory<>("activo"));
        tsl_categoriaOrden.setCellValueFactory(new PropertyValueFactory<>("orden"));
        
        listaCategoria.forEach(e ->{
            tbl_categoria.getItems().add(e);
        });
        
        System.out.print(listaCategoria.size());
        
        //otra forma de hacerlo
        /*for(Categoria c: listaCategoria){
            tbl_categoria.getItems().add(c);
        }*/
    }

    @FXML
    private void clickTable(MouseEvent event) {
        String respuesta ="";
        if(tbl_categoria.getSelectionModel().getSelectedItem() != null){
            categoria = tbl_categoria.getSelectionModel().getSelectedItem();
            tbl_catalogo.getItems().clear();
            respuesta = Requests.get("/catalogo/getCatalogosByIdCategoria/" + categoria.getIdCategoria());
            Gson gson = new Gson();
            
            //Definimos un typetoken que representa una lista de objeto categoria
            TypeToken<List<Catalogo>> token = new TypeToken<List<Catalogo>>(){   
            };
        
            List<Catalogo> listaCatalogo = gson.fromJson(respuesta, token.getType());
        
            tsl_catalogoIdCatalogo.setCellValueFactory(new PropertyValueFactory<>("idCatalogo"));
            tsl_catalogoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tsl_catalogoIdActivo.setCellValueFactory(new PropertyValueFactory<>("activo"));
            tsl_catalogoOrden.setCellValueFactory(new PropertyValueFactory<>("orden"));

            listaCatalogo.forEach(e ->{
                tbl_catalogo.getItems().add(e);
            });

            System.out.print(listaCatalogo.size());

            }
                
    }
}
