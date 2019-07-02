package imc.unp.com.volleysample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button btnEnviar;
    TextView txtMsg;
    RequestQueue queue;
    StringRequest sRequest;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
         /*Instaciar a classe e criar uma fila permanente*/
        queue = SingletonVolley.getInstance(this.getApplicationContext()).getRequestFila();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        btnEnviar = findViewById(R.id.btnEnviar);
        txtMsg = findViewById(R.id.txtMsg);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //enviarRequisicao();
                reqJSON();
            }
        });
    }

    private void reqJSON() {
        String url = "http://www.mocky.io/v2/5d0e36cc3400005200ca4b4f";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = response.getJSONArray("usuario");
                            txtMsg.setText("");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject usuario = jsonArray.getJSONObject(i);
                                String nome = usuario.getString("nome");
                                String email = usuario.getString("email");
                                String sexo = usuario.getString("sexo");
                                txtMsg.append(nome + " " + email + " " + sexo + "\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < jsonArray.length(); i++){

                        }
                        //txtMsg.setText(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Erro! Sem retorno", Toast.LENGTH_SHORT).show();
            }
        });
        SingletonVolley.getInstance(this).addReqFila(jsonObjectRequest);
    }

    private void enviarRequisicao() {

        // chamada básica do volley

        String url = "http://www.google.com.br";
        //queue = Volley.newRequestQueue(this);

        sRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                txtMsg.setText("A resposta é: " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txtMsg.setText("Erro na requisição: " + error);
            }
        }
        );

        // Disparar a fila de Thread de requisição
        //queue.add(sRequest);
        SingletonVolley.getInstance(this).addReqFila(sRequest);

    }
}
