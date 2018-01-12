import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Price } from './price.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PriceService {

    private resourceUrl =  SERVER_API_URL + 'api/prices';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(price: Price): Observable<Price> {
        const copy = this.convert(price);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(price: Price): Observable<Price> {
        const copy = this.convert(price);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Price> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Price.
     */
    private convertItemFromServer(json: any): Price {
        const entity: Price = Object.assign(new Price(), json);
        entity.startDate = this.dateUtils
            .convertDateTimeFromServer(json.startDate);
        entity.endDate = this.dateUtils
            .convertDateTimeFromServer(json.endDate);
        return entity;
    }

    /**
     * Convert a Price to a JSON which can be sent to the server.
     */
    private convert(price: Price): Price {
        const copy: Price = Object.assign({}, price);

        copy.startDate = this.dateUtils.toDate(price.startDate);

        copy.endDate = this.dateUtils.toDate(price.endDate);
        return copy;
    }
}
