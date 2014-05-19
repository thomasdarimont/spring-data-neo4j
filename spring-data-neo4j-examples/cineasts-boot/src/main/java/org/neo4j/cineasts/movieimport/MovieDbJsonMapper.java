package org.neo4j.cineasts.movieimport;

import org.neo4j.cineasts.domain.Movie;
import org.neo4j.cineasts.domain.Person;
import org.neo4j.cineasts.domain.Roles;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class MovieDbJsonMapper {

    public void mapToMovie(Map data, Movie movie, String baseImageUrl) {
        try {
            movie.setTitle((String) data.get("title"));
            movie.setLanguage(extractFirst(data, "spoken_languages", "name"));
            movie.setImdbId((String) data.get("imdb_id"));
            movie.setTagline((String) data.get("tagline"));
            movie.setDescription(limit((String) data.get("overview"), 500));
            movie.setReleaseDate(toDate(data, "release_date", "yyyy-MM-dd"));
            movie.setRuntime((Integer) data.get("runtime"));
            movie.setHomepage((String) data.get("homepage"));
            
            Map trailers = (Map) data.get("trailers");
            movie.setTrailer(extractFirst(trailers, "youtube", "source"));
            
            movie.setGenre(extractFirst(data, "genres", "name"));
            movie.setStudio(extractFirst(data,"production_companies", "name"));
            movie.setImageUrl(buildImageUrl(baseImageUrl, (String) data.get("poster_path")));
        } catch (Exception e) {
            throw new MovieDbException("Failed to map json for movie", e);
        }
    }

    private String buildImageUrl(String baseImageUrl, String imagePath) {
    	if (imagePath == null || imagePath.isEmpty()) 
    		return imagePath;
    	return String.format("%sw185%s", baseImageUrl, imagePath);
    }


    private String extractFirst(Map data, String field, String property) {
        List<Map> inner = (List<Map>) data.get(field);
        if (inner == null || inner.isEmpty()) return null;
        return (String) inner.get(0).get(property);
    }

    private Date toDate(Map data, String field, final String pattern) throws ParseException {
        try {
            String dateString = (String) data.get(field);
            if (dateString == null || dateString.isEmpty()) return null;
            return new SimpleDateFormat(pattern).parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    /*{
    	   "adult":false,
    	   "backdrop_path":"/4NljLiqgOKHPg3QKUgWTyr8QVau.jpg",
    	   "belongs_to_collection":{
    	      "id":74508,
    	      "name":"[REC] Collection",
    	      "poster_path":"/jTA37dMZVUFej20EoM5SomKtnYi.jpg",
    	      "backdrop_path":"/5r6O20g5YMfyUpStc7moUxGmLb8.jpg"
    	   },
    	   "budget":1500000,
    	   "genres":[
    	      {
    	         "id":27,
    	         "name":"Horror"
    	      },
    	      {
    	         "id":10769,
    	         "name":"Foreign"
    	      }
    	   ],
    	   "homepage":"http://www.3l-filmverleih.de/rec",
    	   "id":8329,
    	   "imdb_id":"tt1038988",
    	   "original_title":"[REC]",
    	   "overview":"\"REC\" turns on a young TV reporter and her cameraman who cover the night shift at the 
    	   			  local fire station. Receiving a call from an old lady trapped in her house, they reach her 
    	   			  building to hear horrifying screams -- which begin a long nightmare and a uniquely dramatic 
    	   			  TV report.",
    	   "popularity":1.7814537050628,
    	   "poster_path":"/dYd1GwIZdZLg5uUTr32BIVAGDqt.jpg",
    	   "production_companies":[
    	      {
    	         "name":"Filmax",
    	         "id":3631
    	      }
    	   ],
    	   "production_countries":[
    	      {
    	         "iso_3166_1":"ES",
    	         "name":"Spain"
    	      }
    	   ],
    	   "release_date":"2007-11-01",
    	   "revenue":32492948,
    	   "runtime":78,
    	   "spoken_languages":[
    	      {
    	         "iso_639_1":"es",
    	         "name":"Español"
    	      }
    	   ],
    	   "status":"Released",
    	   "tagline":"One Witness. One Camera",
    	   "title":"[REC]",
    	   "vote_average":6.4,
    	   "vote_count":106,
    	   "casts":{
    	      "cast":[
    	         {
    	            "id":34793,
    	            "name":"Manuela Velasco",
    	            "character":"Ángela Vidal",
    	            "order":0,
    	            "cast_id":1,
    	            "profile_path":"/kQQrHFhTCazfWScBwTaSTtRFWUU.jpg"
    	         },
    	         {
    	            "id":54519,
    	            "name":"Carlos Lasarte",
    	            "character":"César",
    	            "order":4,
    	            "cast_id":3,
    	            "profile_path":"/kP7hWWZsf6GOERaKwcCofiZaBoh.jpg"
    	         },
    	         {
    	            "id":54520,
    	            "name":"Pablo Rosso",
    	            "character":"Marcos",
    	            "order":5,
    	            "cast_id":4,
    	            "profile_path":null
    	         },
    	         {
    	            "id":54521,
    	            "name":"David Vert",
    	            "character":"Alex",
    	            "order":3,
    	            "cast_id":5,
    	            "profile_path":"/gcosWQzYIH7HyqcFE3nUIALTbov.jpg"
    	         },
    	         {
    	            "id":54522,
    	            "name":"Vicente Gil",
    	            "character":"Polizist",
    	            "order":6,
    	            "cast_id":6,
    	            "profile_path":"/6ZppZwmjxyWbELf1LEZa6TL4UE3.jpg"
    	         },
    	         {
    	            "id":54523,
    	            "name":"Martha Carbonell",
    	            "character":"Conchita Izquierdo",
    	            "order":2,
    	            "cast_id":7,
    	            "profile_path":"/vdNJsDSvNggyFRYFAJer6ZTzyph.jpg"
    	         },
    	         {
    	            "id":54524,
    	            "name":"Carlos Vicente",
    	            "character":"Guillem",
    	            "order":7,
    	            "cast_id":8,
    	            "profile_path":"/9GKNu8NJO40CU2t7U4ymBCMV5uE.jpg"
    	         },
    	         {
    	            "id":54532,
    	            "name":"Ferran Terraza",
    	            "character":"Manu",
    	            "order":1,
    	            "cast_id":22,
    	            "profile_path":"/7lvI2rC9emIAkEZeW5zYGr3RKnw.jpg"
    	         },
    	         {
    	            "id":147878,
    	            "name":"Jorge Yamam",
    	            "character":"Sergio",
    	            "order":8,
    	            "cast_id":23,
    	            "profile_path":"/5JTmGMnRGENDzUyOTf62K9vy2Rl.jpg"
    	         }
    	      ],
    	      "crew":[
    	         {
    	            "id":54525,
    	            "name":"Jaume Balagueró",
    	            "department":"Directing",
    	            "job":"Director",
    	            "profile_path":"/zH7TzIJgzrYKbUecPLoTEPP7qxX.jpg"
    	         },
    	         {
    	            "id":54526,
    	            "name":"Paco Plaza",
    	            "department":"Directing",
    	            "job":"Director",
    	            "profile_path":"/kj5tBKhLcC8PcZHwGerEMZakXi5.jpg"
    	         },
    	         {
    	            "id":54525,
    	            "name":"Jaume Balagueró",
    	            "department":"Writing",
    	            "job":"Screenplay",
    	            "profile_path":"/zH7TzIJgzrYKbUecPLoTEPP7qxX.jpg"
    	         },
    	         {
    	            "id":54526,
    	            "name":"Paco Plaza",
    	            "department":"Writing",
    	            "job":"Screenplay",
    	            "profile_path":"/kj5tBKhLcC8PcZHwGerEMZakXi5.jpg"
    	         },
    	         {
    	            "id":54527,
    	            "name":"Luis Berdejo",
    	            "department":"Writing",
    	            "job":"Screenplay",
    	            "profile_path":null
    	         },
    	         {
    	            "id":17083,
    	            "name":"Julio Fernández",
    	            "department":"Production",
    	            "job":"Producer",
    	            "profile_path":null
    	         },
    	         {
    	            "id":37950,
    	            "name":"Carlos Fernández",
    	            "department":"Production",
    	            "job":"Executive Producer",
    	            "profile_path":null
    	         },
    	         {
    	            "id":54528,
    	            "name":"Alberto Marini",
    	            "department":"Production",
    	            "job":"Producer",
    	            "profile_path":null
    	         },
    	         {
    	            "id":54520,
    	            "name":"Pablo Rosso",
    	            "department":"Camera",
    	            "job":"Director of Photography",
    	            "profile_path":null
    	         },
    	         {
    	            "id":54529,
    	            "name":"Gemma Fauria",
    	            "department":"Art",
    	            "job":"Set Designer",
    	            "profile_path":null
    	         },
    	         {
    	            "id":28500,
    	            "name":"David Gallart",
    	            "department":"Editing",
    	            "job":"Editor",
    	            "profile_path":null
    	         },
    	         {
    	            "id":54530,
    	            "name":"Xavier Mas",
    	            "department":"Sound",
    	            "job":"Sound Designer",
    	            "profile_path":null
    	         },
    	         {
    	            "id":54531,
    	            "name":"Glòria Viguer",
    	            "department":"Costume & Make-Up",
    	            "job":"Costume Design",
    	            "profile_path":null
    	         }
    	      ]
    	   },
    	   "trailers":{
    	      "quicktime":[

    	      ],
    	      "youtube":[
    	         {
    	            "name":"Trailer 1",
    	            "size":"Standard",
    	            "source":"YQUkX_XowqI"
    	         }
    	      ]
    	   }
    	}*/

    public void mapToPerson(Map data, Person person, String baseImageUrl) {
        try {
            person.setName((String) data.get("name"));
            person.setBirthday(toDate(data, "birthday", "yyyy-MM-dd"));
            person.setBirthplace((String) data.get("place_of_birth"));
            String biography = (String) data.get("biography");
            person.setBiography(limit(biography,500));
            person.setProfileImageUrl(buildImageUrl(baseImageUrl, (String) data.get("profile_path")));
        } catch (Exception e) {
            throw new MovieDbException("Failed to map json for person", e);
        }
    }

    private String limit(String text, int limit) {
        if (text==null || text.length() < limit) return text;
        return text.substring(0,limit);
    }

    /*{
	   "adult":false,
	   "also_known_as":[
	      "George Glenn Strange",
	      "Glen Strange",
	      "Glen 'Peewee' Strange",
	      "Peewee Strange",
	      "'Peewee' Strange"
	   ],
	   "biography":null,
	   "birthday":"1899-08-16",
	   "deathday":"1973-09-20",
	   "homepage":"",
	   "id":30112,
	   "imdb_id":"nm0833363",
	   "name":"Glenn Strange",
	   "place_of_birth":"Weed, New Mexico, USA",
	   "popularity":0.7,
	   "profile_path":"/ePUp4f4XFguUPgR9wWMTqLg2LeC.jpg"
	}*/


    public Roles mapToRole(String roleString) {
        if (roleString.equals("Actor")) {
            return Roles.ACTS_IN;
        }
        if (roleString.equals("Director")) {
            return Roles.DIRECTED;
        }
        return null;
    }
}
