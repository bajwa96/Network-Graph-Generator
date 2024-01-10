package runner;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class kela {

	public static void main(String[] args) {
		String foods[] = { "kimchi", "miso", "sushi", "moussaka", "ramen", "bulgogi" };
		String cuisines[] = { "korean", "japanese", "japanese", "greek", "japanese", "korean" };
		int ratings[] = { 9, 12, 8, 15, 14, 7 };
		FoodRatings obj = new FoodRatings(foods, cuisines, ratings);
		System.out.println(obj.highestRated("korean"));
		System.out.println(obj.highestRated("japanese"));
		obj.changeRating("sushi", 16);
		System.out.println(obj.highestRated("japanese"));
		obj.changeRating("ramen", 16);
		System.out.println(obj.highestRated("japanese"));

	}

}

class FoodRatings {

	class foods {
		String name;
		String cuisine;
		int rating;

		foods(String name, String cuisine, int rating) {
			this.name = name;
			this.cuisine = cuisine;
			this.rating = rating;
		}
	}

	Map<String, PriorityQueue<foods>> CusineVsPq;
	Map<String, foods> foodsVsObject ;

	public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
		foodsVsObject = new HashMap<>(foods.length);
		CusineVsPq = new HashMap<>();

		for (int i = 0; i < foods.length; i++) {
			foods obj = new foods(foods[i], cuisines[i], ratings[i]);
			foodsVsObject.put(foods[i], obj);
			if (CusineVsPq.containsKey(cuisines[i])) {
				CusineVsPq.get(cuisines[i]).add(obj);
			} else {
				PriorityQueue<foods> sabTuVadiaFood = new PriorityQueue<foods>(new Comparator<foods>() {
					@Override
					public int compare(foods o1, foods o2) {
						int diff = o2.rating - o1.rating;
						if (diff == 0)
							return o1.name.compareTo(o2.name);
						return diff;
					}
				});
				sabTuVadiaFood.add(obj);
				CusineVsPq.put(cuisines[i], sabTuVadiaFood);
			}
		}
//		System.out.println(foodsVsIndex);
//		System.out.println(foodVsCusine);
		System.out.println(CusineVsPq);
	}

	public void changeRating(String food, int newRating) {
		
		foods prev = this.foodsVsObject.get(food);
		PriorityQueue<foods> sabTuVadiaFood = CusineVsPq.get(prev.cuisine);
//		System.out.println(sabTuVadiaFood.remove(prev));
		prev.name="";
//		prev.rating=0;
		foods curr = new foods(food, prev.cuisine, newRating);
//		now.poll();
//		now.remove(food);
//		ratings[foodsVsIndex.get(food)] = newRating;
		sabTuVadiaFood.add(curr);
	}

	public String highestRated(String cuisine) {
		PriorityQueue<foods> sabTuVadiaFood = CusineVsPq.get(cuisine);
		while (sabTuVadiaFood.peek().name.equals("")) {
			sabTuVadiaFood.remove();
		}
		return sabTuVadiaFood.peek().name;
//		return this.CusineVsPq.get(cuisine).peek().name;

	}
}