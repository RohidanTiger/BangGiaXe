package tigerstyle.social.com.banggiaxe.customize.SectionRecycleView;

@SuppressWarnings("WeakerAccess")
public class ItemCoord {

  private int section;
  private int relativePos;

  ItemCoord(int section, int relativePos) {
    this.section = section;
    this.relativePos = relativePos;
  }

  public int section() {
    return section;
  }

  public int relativePos() {
    return relativePos;
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof ItemCoord
        && ((ItemCoord) obj).section() == section()
        && ((ItemCoord) obj).relativePos() == relativePos();
  }

  @Override
  public String toString() {
    return section + ":" + relativePos;
  }
}
