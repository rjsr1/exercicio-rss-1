package br.ufpe.cin.if710.rss

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : Activity() {

    private var RSS_FEED:String ="http://leopoldomt.com/if1001/g1brasil.xml"
    private  var conteudoRSS : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        System.gc()
        setContentView(R.layout.activity_main)
        val viewManager = LinearLayoutManager(this)
        conteudoRSS=findViewById(R.id.conteudoRSS)
        try{
            //chama metodo atraves de doAsync e atualiza o adapter da recyclerView na thread principal
            doAsync {
                val feedXML:String = getFeedXML(RSS_FEED)
                val listaItems=ParserRSS.parse(feedXML)
                uiThread {
                    conteudoRSS?.adapter = RSSAdapter(listaItems,this@MainActivity)
                    Log.d("RSS : ","capturando dados na internet" )
                }

            }
        }catch (e:Exception){
            e.printStackTrace()
        }
        conteudoRSS?.apply {
            layoutManager = viewManager
            setHasFixedSize(true)
        }

    }


    private fun getFeedXML(rsS_FEED: String): String {
        var input: InputStream? = null
        var rssFeed = ""
        try {
            val url = URL(rsS_FEED)
            val conn = url.openConnection() as HttpURLConnection
            input = conn.inputStream
            val out = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var count: Int
            count = input!!.read(buffer)
            while (count  != -1) {
                out.write(buffer, 0, count)
                count = input.read(buffer)//adicionado no porting do codigo para evitar looping infinito
            }
            rssFeed = String(out.toByteArray(),Charsets.UTF_8 )
        } finally {
            input?.close()
        }
        return rssFeed
    }
}
