package com.enclave.barry.recipeapp.util

import android.content.Context
import com.stanfy.gsonxml.GsonXmlBuilder
import com.stanfy.gsonxml.XmlParserCreator
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream

class FileUtil {
    companion object {
        fun readStringFromAssetFile(context: Context, resID: Int): String {
            val inputStream: InputStream = context.resources.openRawResource(resID)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            return String(buffer, Charsets.UTF_8)
        }

        fun <T> xmlToObject(xml: String, classObject: Class<T>): T {
            val parserCreator = XmlParserCreator {
                return@XmlParserCreator XmlPullParserFactory.newInstance().newPullParser()
            }
            val gsonXml =
                GsonXmlBuilder().setXmlParserCreator(parserCreator).setSameNameLists(true).create()
            val output = gsonXml.fromXml(xml, classObject)
            return output
        }

        fun getJsonFromAssets(context: Context, resid: Int): String? {
            val jsonString: String
            jsonString = try {
                val `is`: InputStream = context.resources.openRawResource(resid)
                val size: Int = `is`.available()
                val buffer = ByteArray(size)
                `is`.read(buffer)
                `is`.close()
                String(buffer, Charsets.UTF_8)
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }
            return jsonString
        }
    }
}