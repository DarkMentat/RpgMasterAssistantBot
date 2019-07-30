package org.darkmentat

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.net.URL
import java.util.*

typealias EmogenMap = HashMap<String, HashMap<String, ArrayList<String>>>

object Resources{

    val loader: ClassLoader = this::class.java.classLoader

    object Appearances {
        val genders = loader.getResource("appearance_gender.txt").parseToList()
        val faces = loader.getResource("appearance_faces.txt").parseToList()
        val eyes = loader.getResource("appearance_eyes.txt").parseToList()
        val hair1 = loader.getResource("appearance_hair1.txt").parseToList()
        val hair2 = loader.getResource("appearance_hair2.txt").parseToList()
        val bodies = loader.getResource("appearance_bodies.txt").parseToList()
        val clothes = loader.getResource("appearance_clothes.txt").parseToList()
    }

    object Cyberpunk {
        val buildingFeatures = loader.getResource("cyberpunk_building_features.txt").parseToList()
        val downtown = loader.getResource("cyberpunk_downtown.txt").parseToList()
        val smells = loader.getResource("cyberpunk_smells.txt").parseToList()
        val sounds = loader.getResource("cyberpunk_sounds.txt").parseToList()
    }

    object Emogen {
        val emotions = loader.getResource("emogen_emotions.json").parseJson<EmogenMap>()
    }

    object Natures {
        val natures = loader.getResource("nature_natures.txt").parseToList()
        val motives = loader.getResource("nature_motives.txt").parseToList()
        val manners = loader.getResource("nature_manners.txt").parseToList()
        val fears = loader.getResource("nature_fears.txt").parseToList()
    }

    object Tarot {
        val imagesRaider = loader.getResource("tarot_images_raider.txt").parseToList()
        val imagesNewVision = loader.getResource("tarot_images_new_vision.txt").parseToList()
        val imagesWitches = loader.getResource("tarot_images_witches.txt").parseToList()
    }

    object Names {
        val englishMale = loader.getResource("names_english_male.txt").parseToList()
        val englishFem = loader.getResource("names_english_fem.txt").parseToList()
        val frenchMale = loader.getResource("names_french_male.txt").parseToList()
        val frenchFem = loader.getResource("names_french_fem.txt").parseToList()
        val germanMale = loader.getResource("names_german_male.txt").parseToList()
        val germanFem = loader.getResource("names_german_fem.txt").parseToList()
        val exoticMale = loader.getResource("names_exotic_male.txt").parseToList()
        val exoticFem = loader.getResource("names_exotic_fem.txt").parseToList()
        val japaneseMale = loader.getResource("names_japanese_male.txt").parseToList()
        val japaneseFem = loader.getResource("names_japanese_fem.txt").parseToList()
        val chineseMale = loader.getResource("names_chinese_male.txt").parseToList()
        val chineseFem = loader.getResource("names_chinese_fem.txt").parseToList()
        val nicknames = loader.getResource("names_nicknames.txt").parseToList()
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
