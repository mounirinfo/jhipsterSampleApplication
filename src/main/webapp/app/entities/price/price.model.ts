import { BaseEntity } from './../../shared';

export class Price implements BaseEntity {
    constructor(
        public id?: number,
        public startDate?: any,
        public endDate?: any,
        public products?: BaseEntity[],
    ) {
    }
}
