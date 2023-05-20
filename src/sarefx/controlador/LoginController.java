/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sarefx.controlador;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.json.JSONException;
import sarefx.utils.Window;
import sarefx.api.requests.Requests;
import org.json.JSONObject;
import sarefx.modelo.pojos.Usuario;
import sarefx.utils.JavaUtils;
import javafx.stage.Stage;



public class LoginController implements Initializable {

    @FXML
    private AnchorPane pnr_principal;
    @FXML
    private ImageView img_baner;
    @FXML
    private ImageView img_logo;
    @FXML
    private Pane pnr_panelLogin;
    @FXML
    private Label lbl_usuario;
    @FXML
    private Label lbl_contrasena;
    @FXML
    private TextField txt_usuario;
    @FXML
    private Button btn_iniciar;
    @FXML
    private Button btn_cancelar;
    @FXML
    private PasswordField txt_contrasena;
    @FXML
    private Label lbl_mensaje;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void iniciarSesion(ActionEvent event) throws IOException {
        if(this.validar()){
            
            try {
                this.lbl_mensaje.setText(""); //borrar la etiqueta
                //System.out.print(Window.getStageByEvent(event).getUserData());//mostrar la mac y ip
                
                String data ="";
                
                HashMap<String,Object> params = new LinkedHashMap<>();
                params.put("usuario", this.txt_usuario.getText());
                params.put("contrasena", this.txt_contrasena.getText());
               
                data = Requests.post("/sesion/login/", params);
                
                if(!data.isEmpty()){
                    System.out.println("Data;"+data);
                
                    JSONObject dataJson = new JSONObject(data); //convertimos el string en objeto json

                    if((Boolean)dataJson.get("error") == false){ //validar si encontro algo en la base de datos

                        /*JSONObject respuestaJson = new JSONObject(dataJson.get("respuesta").toString());

                        //Se obtiene el escenario actual apartir del nodo (boton) que desencadena la acción
                        //El metodo getStageByEvent es un metodo personalizado
                        Stage stage = Window.getStageByEvent(event);

                        Usuario u = new Usuario();

                        u.setIdUsuario(respuestaJson.getInt("idUsuario"));              //los parametros que necesites 
                        u.setIdRol(respuestaJson.getInt("idRol"));
                        u.setUsuario(respuestaJson.getString("usuario"));*/

                        Stage stage = Window.getStageByEvent(event);
                        
                        Gson gson = new Gson();
                        Usuario user = gson.fromJson(dataJson.get("respuesta").toString(), Usuario.class); //guarda la informacion del usuario que inicio sesion
                        
                        HashMap<String,Object> context = new HashMap<String,Object> ();             //se agregro
                        context.put("mac",JavaUtils.getMAC());
                        context.put("usuario", user);                                                  //se inserto el objeto
                        context.put("ip",InetAddress.getLocalHost());

                        //System.out.println(respuestaJson);
                        //System.out.println(respuestaJson.getString("nombre"));
                        //System.out.println(respuestaJson.getInt("idRol"));
                        //this.lbl_mensaje.setText(respuestaJson.getString("nombre"));  ---------------------> //para ponerlo en un label o campo de texto

                        //Se carga la nueva interfaz fxml
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sarefx/gui/vista/PrincipalFXML.fxml"));

                        //Se obtiene el nodo padre que contienes los nodos secundarios de la interfaz fxml
                        Parent principal = loader.load();

                        //Se obtiene la instancia del controlador asociado a la interfaz fxml
                        PrincipalController ctrl = loader.getController();

                        //setData es un metodo que esta declarado en la clase PrincipalController
                        //Al metodo setData se le pasa como parametro el contexto
                        ctrl.setData(context);

                        //Se crea una nueva escena con la interfaz principal.fxml
                        Scene scene = new Scene(principal);

                        //Insertamos la nueva escena en el escenario actual
                        //Se puede crear otro escenario para insertar la nueva escena (opcional)
                        stage.setScene(scene);

                        //A la nueva escena se le agrega un titulo
                        stage.setTitle("SARE (Sistema de Administración de Recursos Empresariales)");
                        //Propiedad para no maximizar la escena
                        stage.setResizable(false);
                        //Se muestra la escena que previamente se creo
                        stage.show();


                    }else{
                        //System.out.print(dataJson.get("mensaje").toString());
                        this.lbl_mensaje.setText(dataJson.getString("mensaje"));

                    }

                }else{
                    this.lbl_mensaje.setText("Intentelo mas tarde");
                }
           
            } catch (JSONException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else{
            this.lbl_mensaje.setText("El usuario y contraseña son requeridos...");
            
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Window.close(event);
    }

    @FXML
    private void iniciarSesion2(KeyEvent event) {
    }
    
    
    //validar que los campos no esten vacios
    private boolean validar(){
        
        boolean valido = false;
        
        if(!this.txt_usuario.getText().isEmpty() && !this.txt_contrasena.getText().isEmpty()){
            valido= true;
        }
        
        return valido;
    }
}
