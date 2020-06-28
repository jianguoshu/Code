package com.douzi.dd.demo.inflate;

import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class AdaptXmlResourceParser implements XmlResourceParser {

    private final XmlResourceParser xmlResourceParser;
    private final float ratio;

    public AdaptXmlResourceParser(XmlResourceParser parser, float ratio) {
        this.xmlResourceParser = parser;
        this.ratio = ratio;
    }

    @Override
    public String getAttributeNamespace(int index) {
        return xmlResourceParser.getAttributeNamespace(index);
    }

    @Override
    public void close() {
        xmlResourceParser.close();
    }

    @Override
    public void setFeature(String name, boolean state) throws XmlPullParserException {
        xmlResourceParser.setFeature(name, state);
    }

    @Override
    public boolean getFeature(String name) {
        return xmlResourceParser.getFeature(name);
    }

    @Override
    public void setProperty(String name, Object value) throws XmlPullParserException {
        xmlResourceParser.setProperty(name, value);
    }

    @Override
    public Object getProperty(String name) {
        return xmlResourceParser.getProperty(name);
    }

    @Override
    public void setInput(Reader in) throws XmlPullParserException {
        xmlResourceParser.setInput(in);
    }

    @Override
    public void setInput(InputStream inputStream, String inputEncoding) throws XmlPullParserException {
        xmlResourceParser.setInput(inputStream, inputEncoding);
    }

    @Override
    public String getInputEncoding() {
        return xmlResourceParser.getInputEncoding();
    }

    @Override
    public void defineEntityReplacementText(String entityName, String replacementText) throws XmlPullParserException {
        xmlResourceParser.defineEntityReplacementText(entityName, replacementText);
    }

    @Override
    public int getNamespaceCount(int depth) throws XmlPullParserException {
        return xmlResourceParser.getNamespaceCount(depth);
    }

    @Override
    public String getNamespacePrefix(int pos) throws XmlPullParserException {
        return xmlResourceParser.getNamespacePrefix(pos);
    }

    @Override
    public String getNamespaceUri(int pos) throws XmlPullParserException {
        return xmlResourceParser.getNamespaceUri(pos);
    }

    @Override
    public String getNamespace(String prefix) {
        return xmlResourceParser.getNamespace(prefix);
    }

    @Override
    public int getDepth() {
        return xmlResourceParser.getDepth();
    }

    @Override
    public String getPositionDescription() {
        return xmlResourceParser.getPositionDescription();
    }

    @Override
    public int getLineNumber() {
        return xmlResourceParser.getLineNumber();
    }

    @Override
    public int getColumnNumber() {
        return xmlResourceParser.getColumnNumber();
    }

    @Override
    public boolean isWhitespace() throws XmlPullParserException {
        return xmlResourceParser.isWhitespace();
    }

    @Override
    public String getText() {
        return xmlResourceParser.getText();
    }

    @Override
    public char[] getTextCharacters(int[] holderForStartAndLength) {
        return xmlResourceParser.getTextCharacters(holderForStartAndLength);
    }

    @Override
    public String getNamespace() {
        return xmlResourceParser.getNamespace();
    }

    @Override
    public String getName() {
        return xmlResourceParser.getName();
    }

    @Override
    public String getPrefix() {
        return xmlResourceParser.getPrefix();
    }

    @Override
    public boolean isEmptyElementTag() throws XmlPullParserException {
        return xmlResourceParser.isEmptyElementTag();
    }

    @Override
    public int getAttributeCount() {
        return xmlResourceParser.getAttributeCount();
    }

    @Override
    public String getAttributeName(int index) {
        return xmlResourceParser.getAttributeName(index);
    }

    @Override
    public String getAttributePrefix(int index) {
        return xmlResourceParser.getAttributePrefix(index);
    }

    @Override
    public String getAttributeType(int index) {
        return xmlResourceParser.getAttributeType(index);
    }

    @Override
    public boolean isAttributeDefault(int index) {
        return xmlResourceParser.isAttributeDefault(index);
    }

    @Override
    public String getAttributeValue(int index) {
        return xmlResourceParser.getAttributeValue(index);
    }

    @Override
    public String getAttributeValue(String namespace, String name) {
        return xmlResourceParser.getAttributeValue(namespace, name);
    }

    @Override
    public int getEventType() throws XmlPullParserException {
        return xmlResourceParser.getEventType();
    }

    @Override
    public int next() throws IOException, XmlPullParserException {
        return xmlResourceParser.next();
    }

    @Override
    public int nextToken() throws IOException, XmlPullParserException {
        return xmlResourceParser.nextToken();
    }

    @Override
    public void require(int type, String namespace, String name) throws IOException, XmlPullParserException {
        xmlResourceParser.require(type, namespace, name);
    }

    @Override
    public String nextText() throws IOException, XmlPullParserException {
        return xmlResourceParser.nextText();
    }

    @Override
    public int nextTag() throws IOException, XmlPullParserException {
        return xmlResourceParser.nextTag();
    }

    @Override
    public int getAttributeNameResource(int index) {
        return xmlResourceParser.getAttributeNameResource(index);
    }

    @Override
    public int getAttributeListValue(String namespace, String attribute, String[] options, int defaultValue) {
        return xmlResourceParser.getAttributeListValue(namespace, attribute, options, defaultValue);
    }

    @Override
    public boolean getAttributeBooleanValue(String namespace, String attribute, boolean defaultValue) {
        return xmlResourceParser.getAttributeBooleanValue(namespace, attribute, defaultValue);
    }

    @Override
    public int getAttributeResourceValue(String namespace, String attribute, int defaultValue) {
        return xmlResourceParser.getAttributeResourceValue(namespace, attribute, defaultValue);
    }

    @Override
    public int getAttributeIntValue(String namespace, String attribute, int defaultValue) {
        return xmlResourceParser.getAttributeIntValue(namespace, attribute, defaultValue);
    }

    @Override
    public int getAttributeUnsignedIntValue(String namespace, String attribute, int defaultValue) {
        return xmlResourceParser.getAttributeUnsignedIntValue(namespace, attribute, defaultValue);
    }

    @Override
    public float getAttributeFloatValue(String namespace, String attribute, float defaultValue) {
        return xmlResourceParser.getAttributeFloatValue(namespace, attribute, defaultValue);
    }

    @Override
    public int getAttributeListValue(int index, String[] options, int defaultValue) {
        return xmlResourceParser.getAttributeListValue(index, options, defaultValue);
    }

    @Override
    public boolean getAttributeBooleanValue(int index, boolean defaultValue) {
        return xmlResourceParser.getAttributeBooleanValue(index, defaultValue);
    }

    @Override
    public int getAttributeResourceValue(int index, int defaultValue) {
        return xmlResourceParser.getAttributeResourceValue(index, defaultValue);
    }

    @Override
    public int getAttributeIntValue(int index, int defaultValue) {
        return xmlResourceParser.getAttributeIntValue(index, defaultValue);
    }

    @Override
    public int getAttributeUnsignedIntValue(int index, int defaultValue) {
        return xmlResourceParser.getAttributeUnsignedIntValue(index, defaultValue);
    }

    @Override
    public float getAttributeFloatValue(int index, float defaultValue) {
        return xmlResourceParser.getAttributeFloatValue(index, defaultValue);
    }

    @Override
    public String getIdAttribute() {
        return xmlResourceParser.getIdAttribute();
    }

    @Override
    public String getClassAttribute() {
        return xmlResourceParser.getClassAttribute();
    }

    @Override
    public int getIdAttributeResourceValue(int defaultValue) {
        return xmlResourceParser.getIdAttributeResourceValue(defaultValue);
    }

    @Override
    public int getStyleAttribute() {
        return xmlResourceParser.getStyleAttribute();
    }
}
