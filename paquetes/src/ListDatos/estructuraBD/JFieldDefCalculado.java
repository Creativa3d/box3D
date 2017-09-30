/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ListDatos.estructuraBD;


public class JFieldDefCalculado extends JFieldDef {
  private static final long serialVersionUID = 1493317836282896543L;
  private IFieldDefCalculado moCalculado;



  public JFieldDefCalculado(final int plTipo, final String psNombre, final String psCaption, final boolean pbEsPrincipal, final IFieldDefCalculado poCaculado) {
    super(plTipo, psNombre, psCaption, pbEsPrincipal);
    moCalculado = poCaculado;
    setEditable(false);
    setCalculado(true);
  }

  public JFieldDefCalculado(final String psNombre, final IFieldDefCalculado poCaculado) {
      super(psNombre);
      moCalculado = poCaculado;
      setEditable(false);
      setCalculado(true);
  }

    public String getStringConNull() {
        return getCalculado().getValorCalculado();
    }

    /**
     * @return the moCalculado
     */
    public IFieldDefCalculado getCalculado() {
        return moCalculado;
    }

    /**
     * @param moCalculado the moCalculado to set
     */
    protected void setCalculado(IFieldDefCalculado moCalculado) {
        this.moCalculado = moCalculado;
    }


}
