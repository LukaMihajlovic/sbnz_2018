import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Diagnosis } from './diagnosis.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Diagnosis>;

@Injectable()
export class DiagnosisService {

    private resourceUrl =  SERVER_API_URL + 'api/diagnoses';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(diagnosis: Diagnosis): Observable<EntityResponseType> {
        const copy = this.convert(diagnosis);
        return this.http.post<Diagnosis>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(diagnosis: Diagnosis): Observable<EntityResponseType> {
        const copy = this.convert(diagnosis);
        return this.http.put<Diagnosis>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Diagnosis>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Diagnosis[]>> {
        const options = createRequestOption(req);
        return this.http.get<Diagnosis[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Diagnosis[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Diagnosis = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Diagnosis[]>): HttpResponse<Diagnosis[]> {
        const jsonResponse: Diagnosis[] = res.body;
        const body: Diagnosis[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Diagnosis.
     */
    private convertItemFromServer(diagnosis: Diagnosis): Diagnosis {
        const copy: Diagnosis = Object.assign({}, diagnosis);
        copy.date = this.dateUtils
            .convertLocalDateFromServer(diagnosis.date);
        return copy;
    }

    /**
     * Convert a Diagnosis to a JSON which can be sent to the server.
     */
    private convert(diagnosis: Diagnosis): Diagnosis {
        const copy: Diagnosis = Object.assign({}, diagnosis);
        copy.date = this.dateUtils
            .convertLocalDateToServer(diagnosis.date);
        return copy;
    }

    currentDiagnosis(diagnosis: Diagnosis): Observable<any> {
        let path = this.resourceUrl + '/current'
        return this.http.put<Diagnosis>(path, diagnosis, {
            observe: 'response'
        });
    }
}
