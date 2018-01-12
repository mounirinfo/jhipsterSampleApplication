import { BaseEntity } from './../../shared';

export class Product implements BaseEntity {
    constructor(
        public id?: number,
        public productId?: string,
        public productName?: string,
        public productCategories?: BaseEntity[],
        public priceId?: number,
    ) {
    }
}
