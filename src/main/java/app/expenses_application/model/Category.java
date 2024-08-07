package app.expenses_application.model;

import lombok.Getter;

/**
 * Enumeration representing categories that can be used to classify expenses in the application.
 * When accessing the category drop-down menu, you will find these categories listed.
 */

@Getter
public enum Category {

	FOOD,
	HOME,
	CLOTHES,
	PETS,
	ENTERTAINMENT,
	TRANSPORT,
	EDUCATION,
	MUNICIPAL,
	GIFTS,
	LOANS,
	HEALTH
}