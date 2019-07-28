package org.darkmentat

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.net.URL
import java.util.*

typealias EmogenMap = HashMap<String, HashMap<String, ArrayList<String>>>

object Resources{

    object Appearances {
        val genders = this::class.java.classLoader.getResource("appearance_gender.txt").parseToList()
        val faces = this::class.java.classLoader.getResource("appearance_faces.txt").parseToList()
        val eyes = this::class.java.classLoader.getResource("appearance_eyes.txt").parseToList()
        val hair1 = this::class.java.classLoader.getResource("appearance_hair1.txt").parseToList()
        val hair2 = this::class.java.classLoader.getResource("appearance_hair2.txt").parseToList()
        val bodies = this::class.java.classLoader.getResource("appearance_bodies.txt").parseToList()
        val clothes = this::class.java.classLoader.getResource("appearance_clothes.txt").parseToList()
    }

    object Cyberpunk {
        val buildingFeatures = this::class.java.classLoader.getResource("cyberpunk_building_features.txt").parseToList()
        val downtown = this::class.java.classLoader.getResource("cyberpunk_downtown.txt").parseToList()
        val smells = this::class.java.classLoader.getResource("cyberpunk_smells.txt").parseToList()
        val sounds = this::class.java.classLoader.getResource("cyberpunk_sounds.txt").parseToList()
    }

    object Emogen {
        val emotions = this::class.java.classLoader.getResource("emogen_emotions.json").parseJson<EmogenMap>()
    }


    private fun URL.parseToList(): List<String> {
        return this.readText()
            .lines()
            .filter { !it.startsWith("//") && !it.startsWith("#") && it.isNotBlank() }
    }

    private fun <T> URL.parseJson(): T {
        val typeRef: TypeReference<T> = object : TypeReference<T>() {}
        return this.readText().let { ObjectMapper().readValue(it, typeRef) }
    }
}
