import { BaseEntity } from './../../shared';

export class Category implements BaseEntity {
    constructor(
        public id?: number,
        public categoryName?: string,
        public categoryId?: number,
        public parentCategories?: BaseEntity[],
        public productCategoryRels?: BaseEntity[],
    ) {
    }
}
