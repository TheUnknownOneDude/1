import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GeneralizedString <T> implements StringInterface<T> {
    private List<T> data;
    private int start;
    private int end;

    private GeneralizedString(List<T> data, int start, int end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    public GeneralizedString() {
        this(new ArrayList<T>(), 0, 0);
    }

    public GeneralizedString(ArrayList<T> data) {
        this(data, 0, data.size());
    }

    @Override
    public T at(int i) {
        if (start + i >= end || i < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return data.get(start + i);
    }

    @Override
    public void add(T c) {
        this.insert(this.length(), c);
    }

    @Override
    public void insert(int i, T c) {
        final List<T> cur = new ArrayList<T>(this.length() + 1);

        for (int j = 0; j <= this.length(); j++) {
            if (j == i) {
                cur.add(c);
            }
            if (j != this.length()) {
                cur.add(this.at(j));
            }
        }

        this.data = cur;
        this.start = 0;
        this.end = cur.size();
    }

    @Override
    public int length() {
        return end - start;
    }

    @Override
    public GeneralizedString<T> substring(int i) {
        if (i >= this.length() || i < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return new GeneralizedString<T>(this.data, i, end);
    }

    @Override
    public GeneralizedString<T> substring(int i, int j) {
        if (i >= this.length() || i < 0 || j < i || j > this.length()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return new GeneralizedString<T>(this.data, i, j);
    }

    @Override
    public GeneralizedString<T> concat(@NotNull GeneralizedString<T> s) {
        List<T> res = new ArrayList<>(this.length() + s.length());

        for (int i = start; i < end; i++) {
            res.add(this.at(i));
        }

        for (int i = s.start; i < s.end; i++) {
            res.add(s.at(i));
        }

        return new GeneralizedString<T>(res, 0, res.size());
    }

    @Override
    public boolean beginsWith(GeneralizedString<T> s) {
        if (s == null) {
            return false;
        }

        if (s.length() > this.length()) {
            return false;
        }

        for (int i = 0; i < s.length(); i++) {
            if (s.at(i) == null) {
                if (this.at(i) != null) {
                    return false;
                }
            } else {
                if (!this.at(i).equals(s.at(i))) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean endsWith(GeneralizedString<T> s) {
        if (s == null) {
            return false;
        }
        if (s.length() > this.length()) {
            return false;
        }

        for (int i = 0; i < s.length(); i++) {
            final int sIndex = s.length() - 1 - i;
            final int thisIndex = this.length() - 1 - i;

            if (s.at(sIndex) == null) {
                if (this.at(thisIndex) != null) {
                    return false;
                }
            } else {
                if (!this.at(sIndex).equals(s.at(thisIndex))) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GeneralizedString<?> that = (GeneralizedString<?>) o;
        if (this.length() != that.length()) {
            return false;
        }

        for (int i = 0; i < this.length(); i++) {
            if (!this.at(i).equals(that.at(i))) {
                return false;
            }
        }

        return true;
    }

}
