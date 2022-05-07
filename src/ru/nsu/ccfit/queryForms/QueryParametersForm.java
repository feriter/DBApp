package ru.nsu.ccfit.queryForms;

import ru.nsu.ccfit.InputField;

import java.awt.*;
import java.util.Vector;

public class QueryParametersForm extends Container {
    private final Vector<InputField> inputFields;
    private final Integer queryNumber;

    public QueryParametersForm(int queryNumber) {
        this.queryNumber = queryNumber;

        inputFields = new Vector<>();

        var parameters = ParameterizedQueries.getParameterNames(queryNumber);

        for (int i = 0; i < parameters.size(); ++i) {
            var field = new InputField(parameters.get(i));
            field.setBounds(40,10 + i * 25,400,20);
            inputFields.add(field);
            add(field);
        }

        setBounds(50, 50, 350, 300);
        setVisible(true);
    }

    public String makeCalledQuery() {
        var query = ParameterizedQueries.getQuery(queryNumber);
        assert(query.size() == inputFields.size() + 1):"Incorrect number of parameters";

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < inputFields.size(); ++i) {
            result.append(query.get(i));
            result.append(inputFields.get(i).getFieldText());
        }
        result.append(query.get(inputFields.size()));
        return result.toString();
    }
}
