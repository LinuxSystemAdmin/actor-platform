package im.actor.model.api;
/*
 *  Generated by the Actor API Scheme generator.  DO NOT EDIT!
 */

import im.actor.model.droidkit.bser.Bser;
import im.actor.model.droidkit.bser.BserParser;
import im.actor.model.droidkit.bser.BserObject;
import im.actor.model.droidkit.bser.BserValues;
import im.actor.model.droidkit.bser.BserWriter;
import im.actor.model.droidkit.bser.DataInput;
import im.actor.model.droidkit.bser.DataOutput;
import static im.actor.model.droidkit.bser.Utils.*;
import java.io.IOException;
import im.actor.model.network.parser.*;
import java.util.List;
import java.util.ArrayList;

public class ServiceMessage extends Message {

    private String text;
    private ServiceEx ext;

    public ServiceMessage(String text, ServiceEx ext) {
        this.text = text;
        this.ext = ext;
    }

    public ServiceMessage() {

    }

    public int getHeader() {
        return 2;
    }

    public String getText() {
        return this.text;
    }

    public ServiceEx getExt() {
        return this.ext;
    }

    @Override
    public void parse(BserValues values) throws IOException {
        this.text = values.getString(1);
        if (values.optBytes(3) != null) {
            this.ext = ServiceEx.fromBytes(values.getBytes(3));
        }
    }

    @Override
    public void serialize(BserWriter writer) throws IOException {
        if (this.text == null) {
            throw new IOException();
        }
        writer.writeString(1, this.text);
        if (this.ext != null) {
            writer.writeBytes(3, this.ext.buildContainer());
        }
    }

    @Override
    public String toString() {
        String res = "struct ServiceMessage{";
        res += "text=" + this.text;
        res += ", ext=" + (this.ext != null ? "set":"empty");
        res += "}";
        return res;
    }

}
