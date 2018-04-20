package mx.edu.ittepic.tam_threads_doinbackground_vicentemz;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button2;
    Button button3;
    ProgressBar progressBar;
    EditText numero;
    TextView result;
    String total;
    int total2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        numero=findViewById(R.id.numero);
        result=findViewById(R.id.result);


        button2.setOnClickListener(this);
        button3.setOnClickListener(this);

    }

    private void UnSegundo(){
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){}
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){




            case R.id.button2:

                Hilos();
                EjemploAsyncTask ejemploAsyncTask = new EjemploAsyncTask();
                ejemploAsyncTask.execute();

                break;
            case R.id.button3:

                ejemploAsyncTask = new EjemploAsyncTask();
                ejemploAsyncTask.execute();



                break;
            default:
                break;
        }
    }

    void Hilos(){
        total= numero.getText().toString();
        total2=Integer.parseInt(total);
        new Thread(new Runnable() {
            @Override
            public void run() {

                for(int i=1; i<=total2; i++){
                    UnSegundo();

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "hilo finalizado", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).start();
    }


    private class EjemploAsyncTask extends AsyncTask<Void,Integer,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setMax(100);
            progressBar.setProgress(0);


        }

        @Override
        protected Boolean doInBackground(Void... params) {
            total= numero.getText().toString();
            total2=Integer.parseInt(total);
            for(int i=1; i<=total2; i++){
                UnSegundo();
                publishProgress(i*1);
                if(isCancelled()){
                    break;
                }
                result.setText(" "+i);

            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            progressBar.setProgress(values[0].intValue());

        }



        @Override
        protected void onPostExecute(Boolean resultado) {
            //super.onPostExecute(aVoid);
            if(resultado){

            }


        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            Toast.makeText(getBaseContext(), "Tarea Ha sido cancelada", Toast.LENGTH_SHORT).show();
        }


    }


}
