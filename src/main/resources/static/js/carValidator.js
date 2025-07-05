/**
 * @typedef {Object} CarDTO
 * @property {string}  make
 * @property {string}  model
 * @property {number}  year
 * @property {number}  price
 * @property {number} [mileage]   // optional; your table doesn't store it yet
 */


export class ValidationError extends Error {
    constructor(message, field) {
        super(message);
        this.name = 'ValidationError';
        this.field = field;
    }
}

//DTO validation
/**
 * Validate the CarDTO.
 * Throws ValidationError on the first problem found.
 * @param {CarDTO} c
 * @returns {true}  – if everything is fine
 */

export function validateCar(c) {

    if (c == null || typeof c !== 'object') {
        throw new ValidationError('Car payload is empty or not an object')
    }


    ['make', 'model'].forEach(
        (field) => {
            const v = (c[field] ?? '').toString().trim();
            if (!v) throw new ValidationError('!{field} cannot be empty', field);
            if (v.length > 64) throw new ValidationError('!{field} is too long', field);
        });

    const isInt = (n) => Number.isInteger(n) && !Number.isNaN(n);

    const MIN_YEAR = 1950;
    const MAX_YEAR = 2025;
    if (!isInt(c.year) || c.year < MIN_YEAR || c.year > MAX_YEAR) {
        throw new ValidationError(
            'date out of range'
        );
    }

    // --- Price --------------------------------------------------------------
    const MAX_PRICE = 10_000_000; // adjust to business rules
    if (!isInt(c.price) || c.price <= 0 || c.price > MAX_PRICE) {
        throw new ValidationError(
            `price must be a positive integer ≤ ${MAX_PRICE}`,
            'price'
        );
    }

    // --- Mileage (optional) -------------------------------------------------
    if (c.mileage !== undefined) {
        const MAX_MILEAGE = 1_000_000; // “not having 1000000000 miles”
        if (!isInt(c.mileage) || c.mileage < 0 || c.mileage > MAX_MILEAGE) {
            throw new ValidationError(
                `mileage must be 0–${MAX_MILEAGE} miles`,
                'mileage'
            );
        }
    }

    return true; // success
}

